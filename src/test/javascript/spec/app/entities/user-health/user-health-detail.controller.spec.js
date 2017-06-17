'use strict';

describe('Controller Tests', function() {

    describe('UserHealth Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserHealth, MockBodyPart, MockUserApp;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserHealth = jasmine.createSpy('MockUserHealth');
            MockBodyPart = jasmine.createSpy('MockBodyPart');
            MockUserApp = jasmine.createSpy('MockUserApp');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserHealth': MockUserHealth,
                'BodyPart': MockBodyPart,
                'UserApp': MockUserApp
            };
            createController = function() {
                $injector.get('$controller')("UserHealthDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'stretchCmsApp:userHealthUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
