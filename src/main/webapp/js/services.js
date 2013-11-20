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
  return $resource('rest/customers/:customerId/:driverLicenceId', {});
});
angular.module('taxiService', ['ngResource']).
    factory('Taxis', function($resource){
        return $resource('rest/taxis/:taxiID', {});
    });
angular.module('contractService', ['ngResource']).
    factory('contracts', function($resource){
        return $resource('rest/contracts/:contractID', {});
    });
angular.module('remoteFlightService', ['ngResource']).
    factory('members', function($resource){
        return $resource('http://flightbooking-130254496.rhcloud.com/rest/members/:memberID', {});
    });
angular.module('remoteFlightBookingService', ['ngResource']).
    factory('flights', function($resource){
        return $resource('http://flightbooking-130254496.rhcloud.com/rest/flights/:flightsID', {});
    });

