package com.codebyamir.maxeremin.tutorial.controller;

import com.codebyamir.maxeremin.tutorial.model.Order;
import com.codebyamir.maxeremin.tutorial.model.Route;
import com.codebyamir.maxeremin.tutorial.model.Station;
import com.codebyamir.maxeremin.tutorial.model.User;
import com.codebyamir.maxeremin.tutorial.service.EmailService;
import com.codebyamir.maxeremin.tutorial.service.PaymentService;
import com.codebyamir.maxeremin.tutorial.service.StationService;
import com.codebyamir.maxeremin.tutorial.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegisterController {

    private static final int PRICE_COEFFICIENT = 10;
    private static final int LUGGAGE_COEFFICIENT = 10;
    private static final int ILOVESUTNIK_COUPON = 50;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;
    private StationService stationService;
    private PaymentService paymentService;

    @Autowired
    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, EmailService emailService,
                              StationService stationService, PaymentService paymentService) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
        this.stationService = stationService;
        this.paymentService = paymentService;
    }

    // Return the home page with a startStation list
    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView showHomePage(ModelAndView modelAndView, Route route) {
        modelAndView.addObject("route", route);
        modelAndView.addObject("stationList", stationService.findAll());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm(ModelAndView modelAndView,
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout){
        if (error != null) {
            modelAndView.addObject("errorMessage", "Неверные email и пароль");
        }
        if (logout != null) {
            modelAndView.addObject("msg", "Вы успешно вышли из аккаунта");
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(ModelAndView modelAndView) {
        modelAndView.setViewName("home");
        return modelAndView;
    }*/

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        modelAndView.setViewName("login");
        return modelAndView;
    }*/

    // Find a route
    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public ModelAndView showFindRouteForm(ModelAndView modelAndView) {

        return modelAndView;
    }

    // Show trip
    @RequestMapping(value = "/route", method = RequestMethod.POST)
    public ModelAndView processFindRouteForm(ModelAndView modelAndView, @Valid Route route) {
        if (route.getNumberOfSeats() < 1) {
            modelAndView.addObject("error", "Oops!  You can't define a non-positive amount of passengers");
            modelAndView.setViewName("index");
            return modelAndView;
        }

        Station fromStation = stationService.findById(route.getFromStationName());
        Station toStation = stationService.findById(route.getToStationName());

        double distance = distance(fromStation.getX(), fromStation.getY(), toStation.getX(), toStation.getY(), 'K');
        double distancePrice = distance * PRICE_COEFFICIENT;

        int percentBargain = 1;
        int passengers = route.getNumberOfSeats();
        double totalPrice;
        if (route.getCoupon().equals("ILOVESUTNIK")) {
            totalPrice = distancePrice * (100 - ILOVESUTNIK_COUPON) * 0.01;
            modelAndView.addObject("coupon", ILOVESUTNIK_COUPON);
        } else {
            if (passengers == 1) {
                totalPrice = distancePrice;
            } else if (passengers < 10) {
                percentBargain = passengers * 5;
                totalPrice = distancePrice * (100 - percentBargain) * 0.01;
            } else {
                percentBargain = 50;
                totalPrice = distancePrice * (100 - percentBargain) * 0.01;
            }
        }

        double luggagePrice = route.getLuggage() == 1 ? 0 : route.getLuggage() * LUGGAGE_COEFFICIENT;

        modelAndView.addObject("distance", new DecimalFormat(".##").format(distance));
        modelAndView.addObject("distancePrice", new DecimalFormat(".##").format(distancePrice));
        modelAndView.addObject("passengers", route.getNumberOfSeats());
        modelAndView.addObject("luggage", route.getLuggage());
        modelAndView.addObject("percentBargain", percentBargain);
        modelAndView.addObject("luggagePrice", new DecimalFormat("").format(luggagePrice));
        modelAndView.addObject("totalPrice", new DecimalFormat(".##").format(totalPrice + luggagePrice));
        modelAndView.setViewName("details");
        return modelAndView;
    }

    /**
     * https://stackoverflow.com/a/3694410/5107656
     */

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    // Display forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage() {
        return new ModelAndView("forgotPassword");
    }

    // Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {

        // Lookup user in database by e-mail
        User user = userService.findByEmail(userEmail);

        if (user == null) {
            modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
        } else {

            // Generate random 36-character string token for reset password
            user.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("support@demo.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);

            // Add success message to view
            modelAndView.addObject("successMessage", "A password reset link has been sent to " + userEmail);
        }

        modelAndView.setViewName("forgotPassword");
        return modelAndView;

    }

    // Display form to reset password
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findByResetToken(token);

        if (user != null) { // Token found in DB
            modelAndView.addObject("resetToken", token);
        } else { // Token not found in DB
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
        }

        modelAndView.setViewName("resetPassword");
        return modelAndView;
    }

    // Process reset password form
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {

        // Find the user associated with the reset token
        User user = userService.findByResetToken(requestParams.get("token"));

        // This should always be non-null but we check just in case
        if (user != null) {

            // Set new password
            user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

            // Set the reset token to null so it cannot be used again
            user.setResetToken(null);

            // Save user
            userService.saveUser(user);

            // In order to set a model attribute on a redirect, we must use
            // RedirectAttributes
            redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");

            modelAndView.setViewName("redirect:login");
            return modelAndView;

        } else {
            modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
            modelAndView.setViewName("resetPassword");
        }

        return modelAndView;
    }

    // Going to reset page without a token redirects to login page
    /*@ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }*/

    // Return registration form template
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    // Process form input data
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        // Lookup user in database by e-mail
        User userExists = userService.findByEmail(user.getEmail());

        System.out.println(userExists);

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else { // new user so we create user and send confirmation e-mail

            // Disable user until they click on confirmation link in email
            user.setEnabled(false);

            // Generate random 36-character string token for confirmation link
            user.setConfirmationToken(UUID.randomUUID().toString());

            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();

            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Registration Confirmation");
            registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                    + appUrl + "/confirm?token=" + user.getConfirmationToken());
            registrationEmail.setFrom("noreply@domain.com");

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
            modelAndView.setViewName("register");
        }

        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            modelAndView.addObject("invalidToken", "Ой!  Это неверная страница подтверждения");
        } else { // Token found
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map requestParams, RedirectAttributes redir) {

        modelAndView.setViewName("confirm");

        Zxcvbn passwordCheck = new Zxcvbn();

        Strength strength = passwordCheck.measure(requestParams.get("password").toString());

        if (strength.getScore() < 3) {
            bindingResult.reject("password");

            redir.addFlashAttribute("errorMessage", "Пароль слишком простой. Придумайте пароль по-сложнее.");

            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            System.out.println(requestParams.get("token"));
            return modelAndView;
        }

        // Find the user associated with the reset token
        User user = userService.findByConfirmationToken(requestParams.get("token").toString());

        // Set the role
        user.setRole("USER");

        // Set new password
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password").toString()));

        // Set user to enabled
        user.setEnabled(true);

        // Create Stripe customer
        String id = paymentService.createCustomer(user);

        // Assign ID to user
        user.setStripeCustomerId(id);

        // Save user
        userService.saveUser(user);

        modelAndView.addObject("successMessage", "Ваш пароль успешно сохранен!");
        return modelAndView;
    }

    // Process confirmation link
    @RequestMapping(value="/charge", method = RequestMethod.POST)
    public ModelAndView processCharge(ModelAndView modelAndView, @RequestParam("amount") String amount) {

        User user = userService.findByEmail("eremin.max@gmail.com");

        User tempUser = new User();
        user.setStripeCustomerId("cus_C0d42O4XfWsMGU");

        Order order = new Order();
        order.setChargeAmountDollars(new Double(amount));
        order.setUser(tempUser);
        paymentService.chargeCreditCard(order);

        modelAndView.setViewName("redirect:home");
        return modelAndView;
    }

    @GetMapping("/403")
    public String error403() {
        return "page_403";
    }

}