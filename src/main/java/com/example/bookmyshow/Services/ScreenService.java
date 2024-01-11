package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.ENUMS.Feature;
import com.example.bookmyshow.Models.ENUMS.SeatType;
import com.example.bookmyshow.Models.Screen;
import com.example.bookmyshow.Models.Seat;
import com.example.bookmyshow.Models.Theatre;
import com.example.bookmyshow.Repositories.ScreenRepository;
import com.example.bookmyshow.Repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ScreenService {
    private Scanner scn = new Scanner(System.in);
    private ScreenRepository screenRepository;
    private TheatreRepository theatreRepository;
    private SeatService seatService;

    @Autowired
    public ScreenService(ScreenRepository screenRepository, TheatreRepository theatreRepository,
                         SeatService seatService) {
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
        this.seatService = seatService;
    }
    public Screen addScreen(){
        System.out.println("Please enter the name of the screen");
        String screenName = scn.next();

        Screen screen = new Screen();
        screen.setScreenName(screenName);

        //Adding Features to the Screen
        System.out.println("Please enter the features of the screen. You can enter " +
                "multiple features separated by a comma");
        System.out.println("2D");
        System.out.println("3D");
        System.out.println("2K");
        System.out.println("4K");
        System.out.println("IMAX");
        System.out.println("Dolby_Atmos");
        System.out.println("Dolby_Digital");
        String features = scn.next();
        for(String s : List.of(features.split(","))){
            if(s.equals("2D")){
                screen.getScreenFeatures().add(Feature.TWO_D);
            }
            else if(s.equals("3D")){
                screen.getScreenFeatures().add(Feature.THREE_D);
            }
            else if(s.equals("2K")){
                screen.getScreenFeatures().add(Feature.TWO_K);
            }
            else if(s.equals("4K")){
                screen.getScreenFeatures().add(Feature.FOUR_K);
            }
            else if(s.equals("IMAX")){
                screen.getScreenFeatures().add(Feature.IMAXDISPLAY);
            }
            else if(s.equals("Dolby Atmos")){
                screen.getScreenFeatures().add(Feature.DOLBYATMOS);
            }
            else if(s.equals("Dolby Digital")){
                screen.getScreenFeatures().add(Feature.DOLBYDIGITAL);
            }
        }

        //Adding the Screen to the Theatre
        System.out.println("Please enter the theatre in which the screen is located");
        String theatreName = scn.next();
        Optional<Theatre> theatreOptional = theatreRepository.findByTheatreName(theatreName);
        if(theatreOptional.isEmpty()){
            throw new RuntimeException("Theatre not found");
        }
        theatreOptional.get().getScreenList().add(screen);
        Theatre theatre = theatreRepository.save(theatreOptional.get());
        screen.setTheatre(theatre);

        //Adding Seat Layout for this Screen
        addSeatLayout(screenRepository.save(screen));
        screen.setTotalSeats(screen.getSeatList().size());

        //Save screen to the database and return from the service
        return screenRepository.save(screen);
    }

    private void addSeatLayout(Screen screen) {
        System.out.println("Please enter the number of rows in the screen");
        int rows = scn.nextInt();
        System.out.println("Please enter the number of columns in the screen");
        int columns = scn.nextInt();
        System.out.println("Please enter the number of rows to be allocated for PREMIUM seats");
        int premiumRows = scn.nextInt();
        for(int i=1; i<=premiumRows; i++){
            for(int j=1; j<=columns; j++){
                screen.getSeatList().add(seatService.addSeat(i, j, screen, SeatType.PREMIUM));
            }
        }
        for(int i=premiumRows+1; i<=rows; i++){
            for(int j=1; j<=columns; j++){
                screen.getSeatList().add(seatService.addSeat(i, j, screen, SeatType.ELITE));
            }
        }
    }
}
