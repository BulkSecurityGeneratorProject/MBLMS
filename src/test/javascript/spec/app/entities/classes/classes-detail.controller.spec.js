'use strict';

describe('Controller Tests', function() {

    describe('Classes Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClasses, MockUser, MockSchool, MockProfiles;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClasses = jasmine.createSpy('MockClasses');
            MockUser = jasmine.createSpy('MockUser');
            MockSchool = jasmine.createSpy('MockSchool');
            MockProfiles = jasmine.createSpy('MockProfiles');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Classes': MockClasses,
                'User': MockUser,
                'School': MockSchool,
                'Profiles': MockProfiles
            };
            createController = function() {
                $injector.get('$controller')("ClassesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mblmsApp:classesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
