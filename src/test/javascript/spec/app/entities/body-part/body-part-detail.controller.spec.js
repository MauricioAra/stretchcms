'use strict';

describe('Controller Tests', function() {

    describe('BodyPart Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBodyPart, MockExercise, MockSubCategory, MockUserHealth;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBodyPart = jasmine.createSpy('MockBodyPart');
            MockExercise = jasmine.createSpy('MockExercise');
            MockSubCategory = jasmine.createSpy('MockSubCategory');
            MockUserHealth = jasmine.createSpy('MockUserHealth');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BodyPart': MockBodyPart,
                'Exercise': MockExercise,
                'SubCategory': MockSubCategory,
                'UserHealth': MockUserHealth
            };
            createController = function() {
                $injector.get('$controller')("BodyPartDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'stretchCmsApp:bodyPartUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
