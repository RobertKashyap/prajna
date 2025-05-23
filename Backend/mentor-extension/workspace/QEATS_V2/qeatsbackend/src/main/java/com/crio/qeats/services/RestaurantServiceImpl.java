/*
 *
 * * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.crio.qeats.services;

import com.crio.qeats.dto.Restaurant;
import com.crio.qeats.exchanges.GetRestaurantsRequest;
import com.crio.qeats.exchanges.GetRestaurantsResponse;
import com.crio.qeats.repositoryservices.RestaurantRepositoryService;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

    private final Double peakHoursServingRadiusInKms = 3.0;
    private final Double normalHoursServingRadiusInKms = 5.0;
    @Autowired
    private RestaurantRepositoryService restaurantRepositoryService;


    // TODO: CRIO_TASK_MODULE_RESTAURANTSAPI - Implement findAllRestaurantsCloseby.
    // Check RestaurantService.java file for the interface contract.
    @Override
    public GetRestaurantsResponse findAllRestaurantsCloseBy(
            GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {
        Double servingRadiusInKms;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);

        if ((currentTime.isAfter(LocalTime.parse("07:59:59", formatter))
                && currentTime.isBefore(LocalTime.parse("10:00:01", formatter)))
                || (currentTime.isAfter(LocalTime.parse("12:59:59", formatter))
                        && currentTime.isBefore(LocalTime.parse("14:00:01", formatter)))
                || (currentTime.isAfter(LocalTime.parse("18:59:59", formatter))
                        && (currentTime.isBefore(LocalTime.parse("21:00:01", formatter))))) {
            servingRadiusInKms = peakHoursServingRadiusInKms;
        } else {
            servingRadiusInKms = normalHoursServingRadiusInKms;
        }
        List<Restaurant> restaurants = restaurantRepositoryService.findAllRestaurantsCloseBy(
                getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(),
                currentTime, servingRadiusInKms);
        return new GetRestaurantsResponse(restaurants);
    }

    private boolean isTimeWithInRange(LocalTime timeNow, LocalTime startTime, LocalTime endTime) {
        return timeNow.isAfter(startTime) && timeNow.isBefore(endTime);
    }

    public boolean isPeakHour(LocalTime timeNow) {
        return isTimeWithInRange(timeNow, LocalTime.of(7, 59, 59), LocalTime.of(10, 00, 01))
                || isTimeWithInRange(timeNow, LocalTime.of(12, 59, 59), LocalTime.of(14, 00, 01))
                || isTimeWithInRange(timeNow, LocalTime.of(18, 59, 59), LocalTime.of(21, 00, 01));
    }



    // TODO: CRIO_TASK_MODULE_RESTAURANTSEARCH
    // Implement findRestaurantsBySearchQuery. The request object has the search string.
    // We have to combine results from multiple sources:
    // 1. Restaurants by name (exact and inexact)
    // 2. Restaurants by cuisines (also called attributes)
    // 3. Restaurants by food items it serves
    // 4. Restaurants by food item attributes (spicy, sweet, etc)
    // Remember, a restaurant must be present only once in the resulting list.
    // Check RestaurantService.java file for the interface contract.
    @Override
    public GetRestaurantsResponse findRestaurantsBySearchQuery(
            GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {
        List<List<Restaurant>> restaurantAnsList = new ArrayList<>();

        Double servingRadiusInKms = isPeakHour(currentTime) ? peakHoursServingRadiusInKms
                : normalHoursServingRadiusInKms;
        // ServingRadiusInKMs= 3.0 km

        String searchFor = getRestaurantsRequest.getSearchFor();

        Set<String> restaurantSet = new HashSet<>();

        List<Restaurant> restaurantList = new ArrayList<>();

        if (!searchFor.isEmpty()) {

            // By Name
            restaurantAnsList.add(restaurantRepositoryService.findRestaurantsByName(
                    getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(),
                    searchFor, currentTime, servingRadiusInKms));

            // By Attributes
            restaurantAnsList.add(restaurantRepositoryService.findRestaurantsByAttributes(
                    getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(),
                    searchFor, currentTime, servingRadiusInKms));

            // By ItemName
            restaurantAnsList.add(restaurantRepositoryService.findRestaurantsByItemName(
                    getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(),
                    searchFor, currentTime, servingRadiusInKms));

            // By ItemAttributes
            restaurantAnsList.add(restaurantRepositoryService.findRestaurantsByItemAttributes(
                    getRestaurantsRequest.getLatitude(), getRestaurantsRequest.getLongitude(),
                    searchFor, currentTime, servingRadiusInKms));


            // restaurantList = restaurantAnsList.stream().distinct().collect(Collectors.toList());


            for (List<Restaurant> ListRestIter : restaurantAnsList) {

                for (Restaurant restListRestIterIter : ListRestIter) {

                    if (!restaurantSet.contains(restListRestIterIter.getRestaurantId())) {
                        restaurantSet.add(restListRestIterIter.getName());
                        restaurantList.add(restListRestIterIter);
                    }
                }
            }

            return new GetRestaurantsResponse(restaurantList);

        } else {

            return new GetRestaurantsResponse(new ArrayList<>());

        }
    }

    // TODO: CRIO_TASK_MODULE_MULTITHREADING
    // Implement multi-threaded version of RestaurantSearch.
    // Implement variant of findRestaurantsBySearchQuery which is at least 1.5x time faster than
    // findRestaurantsBySearchQuery.
    @Override
    public GetRestaurantsResponse findRestaurantsBySearchQueryMt(
            GetRestaurantsRequest getRestaurantsRequest, LocalTime currentTime) {

        return null;
    }


}

