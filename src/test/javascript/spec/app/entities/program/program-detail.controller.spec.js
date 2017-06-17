'use strict';

describe('Controller Tests', function() {

    describe('Program Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProgram, MockProgramFeedBack, MockExercise, MockUserApp, MockCalendar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProgram = jasmine.createSpy('MockProgram');
            MockProgramFeedBack = jasmine.createSpy('MockProgramFeedBack');
            MockExercise = jasmine.createSpy('MockExercise');
            MockUserApp = jasmine.createSpy('MockUserApp');
            MockCalendar = jasmine.createSpy('MockCalendar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Program': MockProgram,
                'ProgramFeedBack': MockProgramFeedBack,
                'Exercise': MockExercise,
                'UserApp': MockUserApp,
                'Calendar': MockCalendar
            };
            createController = function() {
                $injector.get('$controller')("ProgramDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'stretchCmsApp:programUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
