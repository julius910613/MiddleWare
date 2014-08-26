/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Define the REST resource service, allowing us to interact with it as a high level service
angular.module('customerService', ['ngResource']).
    factory('Customers', function($resource){
  return $resource('rest/customers/:customerId', {});
});
angular.module('taxiService', ['ngResource']).
    factory('Taxis', function($resource){
        return $resource('rest/taxis/:taxiID', {});
    });
angular.module('contractService', ['ngResource']).
    factory('contracts', function($resource){
        return $resource('rest/contracts/:contractID', {});
    });
angular.module('contractDeleteService', ['ngResource']).
    factory('DeleteContracts', function($resource){
        return $resource('rest/contracts/:contractDate/:customerID', {});
    });
angular.module('remoteFlightCustomerService', ['ngResource']).
    factory('Members', function($resource){
        return $resource('http://flightbooking-130254496.rhcloud.com/rest/members/:memberID/:email', {});
    });
angular.module('remoteFlightQueryService', ['ngResource']).
    factory('flights', function($resource){
        return $resource('http://flightbooking-130254496.rhcloud.com/rest/flights/:flightsID', {});
    });

angular.module('remoteFlightBookingService', ['ngResource']).
    factory('Bookings', function($resource){
        return $resource('http://flightbooking-130254496.rhcloud.com/rest/books/:flightID/:personID', {});
    });

angular.module('remoteHotelCustomerService', ['ngResource']).
    factory('HotelCustomers', function($resource){
        return $resource('http://hotelbooking-130254496.rhcloud.com/rest/members/:memberID/:email', {});
    });
angular.module('remoteHotelsQueryService', ['ngResource']).
    factory('Hotels', function($resource){
        return $resource('http://hotelbooking-130254496.rhcloud.com/rest/flights/:flightsID', {});
    });

angular.module('remoteHotelBookingService', ['ngResource']).
    factory('HotelBookings', function($resource){
        return $resource('http://hotelbooking-130254496.rhcloud.com/rest/books/:flightID/:personID', {});
    });