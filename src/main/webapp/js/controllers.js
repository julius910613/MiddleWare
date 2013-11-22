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
function TaxisCtrl($scope, $http, Taxis) {

    // Define a refresh function, that updates the data from the REST service
    $scope.refresh = function () {
        $scope.taxis = Taxis.query();
    };

    // Define a reset function, that clears the prototype newMember object, and
    // consequently, the form
    $scope.reset = function () {
        // clear input fields
        $scope.newTaxi = {};
    };

    // Define a register function, which adds the member using the REST service,
    // and displays any error messages
    $scope.register = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};
        console.log($scope.newTaxi);
        Taxis.save($scope.newTaxi, function (data) {

            // mark success on the registration form
            $scope.successMessages = [ 'Taxi Registered' ];

            // Update the list of members
            $scope.refresh();

            // Clear the form
            $scope.reset();
        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;

            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            $scope.$apply();
        });

    };

    // Call the refresh() function, to populate the list of members
    $scope.refresh();

    // Initialize newMember here to prevent Angular from sending a request
    // without a proper Content-Type.
    $scope.reset();

    // Set the default orderBy to the name property
    $scope.orderBy = 'id';
}
function MembersCtrl($scope, Customers, Members) {

    // Define a refresh function, that updates the data from the REST service
    $scope.refresh = function () {
        $scope.customers = Customers.query();
        $scope.members = Members.query();
    };

    // Define a reset function, that clears the prototype newMember object, and
    // consequently, the form
    $scope.reset = function () {
        // clear input fields
        $scope.newMember = {};

    };

    $scope.register = function () {
        console.log($scope.newMember);

        $scope.registerCustomer();
        $scope.registerRemoteCustomer();
    }

    // Define a register function, which adds the member using the REST service,
    // and displays any error messages
    $scope.registerCustomer = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Customers.save($scope.newMember, function (data) {

            // mark success on the registration form
            $scope.successMessages = [ 'customer Registered' ];

            // Update the list of members
            $scope.refresh();

            // Clear the form
            $scope.reset();
        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
                console.log(result.data);
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            //$scope.$apply();
        });

    };

    $scope.registerRemoteCustomer = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Members.save($scope.newMember, function (data) {


            // mark success on the registration form
            $scope.successMessages = [ 'customer Registered' ];

            // Update the list of members
            $scope.refresh();

            // Clear the form
            $scope.reset();
        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
                console.log(result.data);
            } else {
                console.log(result);
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            //$scope.$apply();
        });

    };

    // Call the refresh() function, to populate the list of members
    $scope.refresh();

    // Initialize newMember here to prevent Angular from sending a request
    // without a proper Content-Type.
    $scope.reset();

    // Set the default orderBy to the name property
    $scope.orderBy = 'id';
}
function ContractCtrl($scope, $http, $resource, Taxis, Customers, contracts, flights, Members, Bookings, DeleteContracts) {

    // Define a refresh function, that updates the data from the REST service
    $scope.refresh = function () {
        $scope.taxis = Taxis.query();
        $scope.customers = Customers.query();
        $scope.contracts = contracts.query();
        $scope.Flights = flights.query();
        $scope.members = Members.query();
        $scope.bookings = Bookings.query();
        $scope.deletecontracts = DeleteContracts.query();
    };

    // Define a reset function, that clears the prototype newMember object, and
    // consequently, the form
    $scope.reset = function () {
        // clear input fields
        $scope.newcontract = {};
        $scope.deleteContract = {};
        $scope.localContract = {};
        $scope.remoteNewContract = {};
        $scope.deleteContractForError = {};
        $scope.deleteRemoteContractForErroe = {};
        $scope.remoteNewCustomer = {};

    };
//
    $scope.delete = function () {
        contracts.get({contractID: $scope.deleteContract.id}, function (data) {
            $scope.deleteContract = data;
            $scope.contracts.splice($scope.deleteContract.id, 1);
            contracts.delete({contractID: $scope.deleteContract.id}, function (data) {

                $scope.successMessages = ['Contract Delected'];
            });


        });


    };


    $scope.deleteRemoteWhenErrorHappen = function () {
        $scope.deleteContractForError.customer = $scope.newcontract.customer;
        $scope.deleteContractForError.taxi = $scope.newcontract.taxi;
        $scope.deleteContractForError.contractDate = $scope.newcontract.contractDate;
        DeleteContracts.delete({customerID: $scope.deleteContractForError.customer.id, contractDate: $scope.deleteContractForError.contractDate}, function (data) {
            $scope.successMessages = ['Contract Delected'];
        });
    };


    $scope.deleteWhenErrorHappen = function () {
        $scope.deleteContractForError.customer = $scope.newcontract.customer;
        $scope.deleteContractForError.taxi = $scope.newcontract.taxi;
        $scope.deleteContractForError.contractDate = $scope.newcontract.contractDate;
        console.log( $scope.deleteContractForError.customer);
        DeleteContracts.delete({customerID: $scope.deleteContractForError.customer.id, contractDate: $scope.deleteContractForError.contractDate}, function (data) {
            console.log(data);
            $scope.successMessages = ['Contract Delected'];
        },function(result){
            console.log(result);
        });
    };


    $scope.setCustomer = function () {
        Customers.get({customerId: $scope.newcontract.customer}, function (data) {
            $scope.newcontract.customer = data;
            console.log($scope.newcontract.customer);

        });
    };

    $scope.setTaxi = function () {
        Taxis.get({taxiID: $scope.newcontract.taxi}, function (data) {
            $scope.newcontract.taxi = data;
            console.log($scope.newcontract.taxi);
        });

    };
    $scope.register = function () {
        Customers.get({customerId: $scope.newcontract.customer}, function (data) {
            $scope.newcontract.customer = data;
            console.log($scope.newcontract.customer);
            Taxis.get({taxiID: $scope.newcontract.taxi}, function (data) {
                $scope.newcontract.taxi = data;
                console.log($scope.newcontract.taxi);
                flights.get({flightsID: $scope.newcontract.flightID}, function (data) {
                    $scope.remoteNewContract.flight = data;
                    Members.get({memberID: 'check', email: $scope.newcontract.customer.email}, function (data) {

                        if(data.length == 0){
                           $scope.remoteNewCustomer = $scope.newcontract;
                           $scope.registerRemoteCustomer();
                            Members.get({memberID: 'a', email: $scope.newcontract.customer.email}, function (data) {

                                $scope.remoteNewContract.member = data;
                                $scope.localContract.customer = $scope.newcontract.customer;
                                $scope.localContract.taxi = $scope.newcontract.taxi;
                                $scope.localContract.contractDate = $scope.newcontract.contractDate;
                                console.log($scope.localContract);
                                console.log($scope.remoteNewContract);

                                $scope.registercontract();
                                $scope.registerRemoteContract();
                            },function(result){
                                console.log(result.status);
                            });
                        } else{
                            $scope.remoteNewContract.member = data;
                            $scope.localContract.customer = $scope.newcontract.customer;
                            $scope.localContract.taxi = $scope.newcontract.taxi;
                            $scope.localContract.contractDate = $scope.newcontract.contractDate;
                            console.log($scope.localContract);
                            console.log($scope.remoteNewContract);

                            $scope.registercontract();
                            $scope.registerRemoteContract();
                        }


                    });

                });

            });


        });
        $scope.reset();
    };

    $scope.registerRemoteCustomer = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Members.save($scope.remoteNewCustomer, function (data) {

            console.log($scope.localContract) ;
            // mark success on the registration form
            $scope.successMessages = [ 'customer Registered' ];

            // Update the list of members
            $scope.refresh();


        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
                console.log(result.data);
            } else {
                console.log(result);
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            //$scope.$apply();
        });
        // Clear the form

    };


    $scope.registerRemoteContract = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Bookings.save($scope.remoteNewContract, function (data) {
            console.log($scope.localContract) ;
            // mark success on the registration form
            $scope.successMessages = [ 'contract Registered' ];

            // Update the list of members
            $scope.refresh();

        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
                console.log(result.data);
                console.log($scope.localContract) ;
                $scope.deleteWhenErrorHappen();
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
                $scope.deleteWhenErrorHappen();
            }

        });



    };


    // Define a register function, which adds the member using the REST service,
    // and displays any error messages
    $scope.registercontract = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        contracts.save($scope.localContract, function (data) {
            console.log($scope.localContract) ;
            // mark success on the registration form
            $scope.successMessages = [ 'contract Registered' ];

            // Update the list of members
            $scope.refresh();


        }, function (result) {
            if ((result.status == 409) || (result.status == 400)) {
                $scope.errors = result.data;
                console.log(result.data);
            } else {
                $scope.errorMessages = [ 'Unknown  server error' ];
            }
            $scope.$apply();
        });
        // Clear the form

    };

    // Call the refresh() function, to populate the list of members
    $scope.refresh();

    // Initialize newMember here to prevent Angular from sending a request
    // without a proper Content-Type.
    $scope.reset();

    // Set the default orderBy to the name property
    $scope.orderBy = 'id';
}