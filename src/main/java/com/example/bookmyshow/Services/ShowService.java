package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.*;
import com.example.bookmyshow.Models.ENUMS.SeatStatus;
import com.example.bookmyshow.Models.ENUMS.SeatType;
import com.example.bookmyshow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShowService {
    private Scanner scn = new Scanner(System.in);
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private ShowSeatMappingRepository showSeatMappingRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;

    @Autowired
    public ShowService(MovieRepository movieRepository, ScreenRepository screenRepository,
                       ShowSeatMappingRepository showSeatMappingRepository,
                       ShowSeatTypeRepository showSeatTypeRepository,
                       ShowRepository showRepository,
                       SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.showSeatMappingRepository = showSeatMappingRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    public Show addShow(){
        System.out.println("Please enter the name of the movie");
        String movieName = scn.next();
        System.out.println("Please enter the name of the screen");
        String screenName = scn.next();
        System.out.println("Please mention the show date in the format dd/mm/yyyy");
        String showDate = scn.next();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Please mention the show start time in the format hh:mm:ss");
        String showStartTime = scn.next();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
        Show show = new Show();
        show.setShowDate(LocalDate.parse(showDate, dateFormatter));
        show.setShowStartTime(LocalTime.parse(showStartTime, timeFormatter));

        if(movieRepository.findByMovieName(movieName).isEmpty()){
            throw new RuntimeException("Movie not found");
        }
        movieRepository.findByMovieName(movieName).get().getShows().add(show);
        Movie movie = movieRepository.save(movieRepository.findByMovieName(movieName).get());
        show.setMovie(movie);

        if (screenRepository.findByScreenName(screenName).isEmpty()) {
            throw new RuntimeException("Screen not found");
        }
        screenRepository.findByScreenName(screenName).get().getShowList().add(show);
        Screen screen = screenRepository.save(screenRepository.findByScreenName(screenName).get());
        show.setScreen(screen);

        //Adding ShowSeatMapping for this Show
        addShowSeatMapping(showRepository.save(show), screen.getSeatList());

        //Adding ShowSeatType for this Show
        addShowSeatType(showRepository.save(show));
        return showRepository.save(show);
    }

    private void addShowSeatType(Show show) {
        System.out.println("Please enter the price of the PREMIUM seats");
        int premiumPrice = scn.nextInt();
        System.out.println("Please enter the price of the ELITE seats");
        int elitePrice = scn.nextInt();
        ShowSeatType showEliteSeatType = new ShowSeatType(show, SeatType.ELITE, elitePrice);
        ShowSeatType showPremiumSeatType = new ShowSeatType(show, SeatType.PREMIUM, premiumPrice);
        show.getShowSeatTypeList().add(showSeatTypeRepository.save(showEliteSeatType));
        show.getShowSeatTypeList().add(showSeatTypeRepository.save(showPremiumSeatType));
    }

    private void addShowSeatMapping(Show show, List<Seat> seatList) {
        for(Seat seat : seatList){
            ShowSeatMapping showSeatMapping = new ShowSeatMapping();
            showSeatMapping.setShow(show);
            showSeatMapping.setSeat(seat);
            showSeatMapping.setSeatStatus(SeatStatus.AVAILABLE);
            show.getShowSeatMappingList().add(showSeatMappingRepository.save(showSeatMapping));
        }
    }

    public List<LocalDate> fetchShowDatesForMovie(Movie movie){
        List<LocalDate> showDates = new ArrayList<>();
        for(Show s : movie.getShows()){
            if(!showDates.contains(s.getShowDate())){
                showDates.add(s.getShowDate());
            }
        }
        /*
        List<LocalDate> showDates = showRepository.findAllByMovieId(movie.getId());
         */
        return showDates;
    }

    public List<Show> fetchShowsForMovieAndDate(Movie movie, LocalDate showDateLocal) {
        List<Show> shows = new ArrayList<>();
        for(Show s : movie.getShows()){
            if(s.getShowDate().equals(showDateLocal)){
                shows.add(s);
            }
        }
        return shows;
    }

    public List<Theatre> fetchTheatresForShowsAndCity(List<Show> shows, String cityName){
        List<Theatre> theatres = new ArrayList<>();
        for(Show s : shows){
            if((!theatres.contains(s.getScreen().getTheatre())) &&
                    (s.getScreen().getTheatre().getCity().getCityName().equals(cityName))){
                theatres.add(s.getScreen().getTheatre());
            }
        }
        return theatres;
    }

    public void displayShowsForMovieAndDate(List<Show> shows, String cityName){

        //Finding the theatres involved in the filtered shows
        List<Theatre> theatres = fetchTheatresForShowsAndCity(shows, cityName);

        //Displaying the show details in a readable format
        for(Theatre t : theatres){
            boolean theatreFlag = false;
            for(Screen s : t.getScreenList()){
                for(Show show : s.getShowList()){
                    if(shows.contains(show)){
                        if(!theatreFlag){
                            System.out.println(t.getTheatreName());
                            theatreFlag = true;
                            //System.out.println();
                        }
                        System.out.println(s.getScreenName());
                        System.out.println(show.getId() + " " + show.getShowStartTime());
                    }
                }
                //System.out.println();
            }
            System.out.println();
        }
    }

    public Show getShow(Long showId){
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new RuntimeException("Show not found");
        }
        return showOptional.get();
    }

    public String getCharForNumber(int i) {
        StringBuffer sb = new StringBuffer();
        while (i > 0) {
            int rem = i % 26;
            if (rem == 0) {
                sb.append('Z');
                i = (i / 26) - 1;
            } else {
                sb.append((char)((rem - 1) + 'A'));
                i = i / 26;
            }
        }
        return sb.reverse().toString();
    }

    public String convertRowColToString(int row, int col, char[] c, Long seatId){
        String rowString = getCharForNumber(row);
        String colString = Integer.toString(col);
        String seatIdString = Long.toString(seatId);
        Arrays.fill(c, ' ');
        c[0] = '|';
        c[14] = '|';
        c[7] = '_';
        for(int i=7-seatIdString.length(); i<7; i++){
            c[i] = seatIdString.charAt(i-(7-seatIdString.length()));
        }
        for(int i=8; i<8+rowString.length(); i++){
            c[i] = rowString.charAt(i-8);
        }
        for(int i=8+rowString.length(); i<8+rowString.length()+colString.length(); i++){
            c[i] = colString.charAt(i-(8+rowString.length()));
        }
        /*Seat seat = seatRepository.findById(seatId).get();
        seat.setSeatNumber(rowString + colString);
        seat = seatRepository.save(seat);*/
        return String.valueOf(c);
    }

    public void displaySeatLayoutForShow(Show show){
        List<ShowSeatMapping> showSeatMappings = show.getShowSeatMappingList();
        Seat lastSeat = showSeatMappings.get(showSeatMappings.size()-1).getSeat();
        int count = 0;
        char[] c = new char[15];
        for(ShowSeatMapping smp : showSeatMappings){
            if(smp.getSeatStatus().equals(SeatStatus.AVAILABLE)){
                String seatRep = convertRowColToString(smp.getSeat().getSeatRow(), smp.getSeat().getSeatCol(), c, smp.getId());
                System.out.print(seatRep + " ");
                count++;
            }
            else{
                Arrays.fill(c, ' ');
                c[0] = '|';
                c[14] = '|';
                c[7] = 'N';
                c[8] = 'A';
                System.out.print(String.valueOf(c) + " ");
                count++;
            }
            if(count == lastSeat.getSeatCol()){
                //System.out.print(smp.getId() + "_" + smp.getSeat().getSeatRow() + "" + smp.getSeat().getSeatCol());
                count = 0;
                System.out.println();
            }
        }
        System.out.println();
    }

    public List<Long> returnShowSeatMappingIds(String seatIds){
        List<String> seatIdsList = Arrays.asList(seatIds.split(","));
        List<Long> showSeatIds = new ArrayList<>();
        for(String s : seatIdsList){
            List<String> seatId = Arrays.asList(s.split("_"));
            showSeatIds.add(Long.parseLong(seatId.get(0)));
        }
        return showSeatIds;
    }
}
