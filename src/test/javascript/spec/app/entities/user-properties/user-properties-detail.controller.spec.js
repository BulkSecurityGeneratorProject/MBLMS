'use strict';

describe('Controller Tests', function() {

    describe('UserProperties Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserProperties, MockUser, MockSchool;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserProperties = jasmine.createSpy('MockUserProperties');
            MockUser = jasmine.createSpy('MockUser');
            MockSchool = jasmine.createSpy('MockSchool');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserProperties': MockUserProperties,
                'User': MockUser,
                'School': MockSchool
            };
            createController = function() {
                $injector.get('$controller')("UserPropertiesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mblmsApp:userPropertiesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
