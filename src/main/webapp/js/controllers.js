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
function MembersCtrl($scope, Customers, members) {

    // Define a refresh function, that updates the data from the REST service
    $scope.refresh = function () {
        $scope.customers = Customers.query();
        $scope.members = members.query();
    };

    // Define a reset function, that clears the prototype newMember object, and
    // consequently, the form
    $scope.reset = function () {
        // clear input fields
        $scope.newMember = {};

    };

    $scope.register = function () {

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

        members.save($scope.newMember, function (data) {

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
                console.log(result.data);
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
function ContractCtrl($scope, $http, $resource, Taxis, Customers, contracts) {

    // Define a refresh function, that updates the data from the REST service
    $scope.refresh = function () {
        $scope.taxis = Taxis.query();
        $scope.customers = Customers.query();
        $scope.contracts = contracts.query();
    };

    // Define a reset function, that clears the prototype newMember object, and
    // consequently, the form
    $scope.reset = function () {
        // clear input fields
        $scope.newcontract = {};
        $scope.deleteContract = {};
        $scope.addedCustomer = {};
        $scope.remoteMember = {};
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
                $scope.registercontract();
            });


        });
    };

    $scope.registerCustomerAndContract = function () {
        $scope.addedCustomer.name = $scope.newMember.name;
        $scope.addedCustomer.password = $scope.newMember.password;
        $scope.addedCustomer.driverLicenseID = $scope.newMember.driverLicenseID;
        $scope.remoteMember.name = $scope.newMember.name;
        $scope.remoteMember.email = $scope.newMember.email;
        $scope.remoteMember.phoneNumber = $scope.newMember.phoneNumber;
        $scope.registerCustomer();
        $scope.registerRemoteCustomer();
        $scope.newcontract.customer = $scope.addedCustomer;
        console.log($scope.newcontract.customer);
        Taxis.get({taxiID: $scope.newcontract.taxi}, function (data) {
            $scope.newcontract.taxi = data;
            console.log($scope.newcontract.taxi);
            $scope.registercontract();
        });
    };


    $scope.registerRemoteContract = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        Customers.save($scope.addedCustomer, function (data) {

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
            $scope.$apply();
        });

    };

    $scope.registerRemoteCustomer = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        members.save($scope.remoteMember, function (data) {

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
            $scope.$apply();
        });

    };

    $scope.registerNewContract = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        contracts.save($scope.newcontract, function (data) {

            // mark success on the registration form
            $scope.successMessages = [ 'contract Registered' ];

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
            $scope.$apply();
        });

    };


    // Define a register function, which adds the member using the REST service,
    // and displays any error messages
    $scope.registercontract = function () {
        $scope.successMessages = '';
        $scope.errorMessages = '';
        $scope.errors = {};

        contracts.save($scope.newcontract, function (data) {

            // mark success on the registration form
            $scope.successMessages = [ 'contract Registered' ];

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