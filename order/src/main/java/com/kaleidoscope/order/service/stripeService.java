package com.kaleidoscope.order.service;

import com.kaleidoscope.order.dto.StripeDto;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class stripeService {

    @Value("${stripe.apikey}")
    private String stripeApiKey;

    public Map<String, Object> createPaymentIntent(StripeDto stripeDto) {
        Stripe.apiKey = stripeApiKey;


        try {
            long amount = stripeDto.getAmount();
            String currency = stripeDto.getCurrency();
            // String paymentMethodId = stripeDto.getMethodType();

            System.out.println("latest"+amount+currency);
            

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    // .addPaymentMethodType("card")
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // PaymentIntentConfirmParams confirmParams = PaymentIntentConfirmParams.builder()
            //         .setPaymentMethod(paymentMethodId)
            //         .build();

            // paymentIntent = paymentIntent.confirm(confirmParams);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("clientSecret", paymentIntent.getClientSecret());
            responseData.put("status", paymentIntent.getStatus());
            responseData.put("paymentIntentId", paymentIntent.getId());

            return responseData;

        } catch (Exception e) {

            e.printStackTrace();

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to create or confirm payment intent.");
            errorResponse.put("message", e.getMessage());

            return errorResponse;
        }
    }
}
