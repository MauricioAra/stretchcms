'use strict';

describe('Controller Tests', function() {

    describe('UserApp Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserApp, MockUser, MockUserHealth, MockProgram, MockUserVitality, MockExercise;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserApp = jasmine.createSpy('MockUserApp');
            MockUser = jasmine.createSpy('MockUser');
            MockUserHealth = jasmine.createSpy('MockUserHealth');
            MockProgram = jasmine.createSpy('MockProgram');
            MockUserVitality = jasmine.createSpy('MockUserVitality');
            MockExercise = jasmine.createSpy('MockExercise');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserApp': MockUserApp,
                'User': MockUser,
                'UserHealth': MockUserHealth,
                'Program': MockProgram,
                'UserVitality': MockUserVitality,
                'Exercise': MockExercise
            };
            createController = function() {
                $injector.get('$controller')("UserAppDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'stretchCmsApp:userAppUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
