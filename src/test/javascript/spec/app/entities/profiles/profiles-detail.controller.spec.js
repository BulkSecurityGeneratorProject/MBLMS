'use strict';

describe('Controller Tests', function() {

    describe('Profiles Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfiles, MockUser, MockClasses;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfiles = jasmine.createSpy('MockProfiles');
            MockUser = jasmine.createSpy('MockUser');
            MockClasses = jasmine.createSpy('MockClasses');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Profiles': MockProfiles,
                'User': MockUser,
                'Classes': MockClasses
            };
            createController = function() {
                $injector.get('$controller')("ProfilesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mblmsApp:profilesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
