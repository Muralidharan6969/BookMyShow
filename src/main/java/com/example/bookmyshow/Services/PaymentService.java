package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.ENUMS.PaymentProvider;
import com.example.bookmyshow.Models.ENUMS.PaymentStatus;
import com.example.bookmyshow.Models.ENUMS.PaymentType;
import com.example.bookmyshow.Models.Payment;
import com.example.bookmyshow.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class PaymentService {
    private Scanner scn = new Scanner(System.in);
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment paymentHelper(Payment payment) {
        payment.setPaymentProvider(PaymentProvider.RAZORPAY);
        System.out.println("Kindly choose your preferred payment method from the following: ");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Net Banking");
        System.out.println("4. UPI");
        int choice = scn.nextInt();
        switch (choice) {
            case 1:
                payment.setPaymentType(PaymentType.CREDITCARD);
                System.out.println("Please enter your credit card number");
                String creditCardNumber = scn.next();
                System.out.println("Please enter your CVV");
                String cvv = scn.next();
                System.out.println("Please enter your card expiry date in the format MM/YYYY");
                String expiryDate = scn.next();
                System.out.println("Your payment has been successfully processed");
                break;
            case 2:
                payment.setPaymentType(PaymentType.DEBITCARD);
                System.out.println("Please enter your debit card number");
                String debitCardNumber = scn.next();
                System.out.println("Please enter your CVV");
                String cvv1 = scn.next();
                System.out.println("Please enter your card expiry date in the format MM/YYYY");
                String expiryDate1 = scn.next();
                System.out.println("Your payment has been successfully processed");
                break;
            case 3:
                payment.setPaymentType(PaymentType.NETBANKING);
                System.out.println("Please enter your bank name");
                String bankName = scn.next();
                System.out.println("Please enter your bank account number");
                String bankAccountNumber = scn.next();
                System.out.println("Please enter your bank IFSC code");
                String bankIfscCode = scn.next();
                System.out.println("Your payment has been successfully processed");
                break;
            case 4:
                payment.setPaymentType(PaymentType.UPI);
                System.out.println("Please enter your UPI ID");
                String upiId = scn.next();
                System.out.println("Your payment has been successfully processed");
                break;
            default:
                System.out.println("Invalid choice");
                payment.setPaymentStatus(PaymentStatus.CANCELLED);
        }
        return payment;
    }

    public Payment makePayment(int totalAmount) {
        Payment payment = new Payment();
        System.out.println("The amount payable is " + "Rs. " + totalAmount);
        System.out.println("Do you confirm the payment? (Y/N)");
        String confirmation = scn.next();
        if (confirmation.equals("Y")) {
            payment.setPaymentStatus(PaymentStatus.INPROGRESS);
            Payment payment1 = paymentHelper(payment);
            if(payment1.getPaymentStatus().equals(PaymentStatus.INPROGRESS)) {
                payment.setTotalAmount(totalAmount);
                payment.setPaymentStatus(PaymentStatus.CONFIRMED);
            }
            else if (payment1.getPaymentStatus().equals(PaymentStatus.CANCELLED)){
                payment.setPaymentStatus(PaymentStatus.CANCELLED);
            }
        } else {
            payment.setPaymentStatus(PaymentStatus.CANCELLED);
        }
        return paymentRepository.save(payment);
    }
}
