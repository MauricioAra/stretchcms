'use strict';

describe('Controller Tests', function() {

    describe('BodyPoint Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBodyPoint, MockUserApp, MockBodyPointRegistry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBodyPoint = jasmine.createSpy('MockBodyPoint');
            MockUserApp = jasmine.createSpy('MockUserApp');
            MockBodyPointRegistry = jasmine.createSpy('MockBodyPointRegistry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BodyPoint': MockBodyPoint,
                'UserApp': MockUserApp,
                'BodyPointRegistry': MockBodyPointRegistry
            };
            createController = function() {
                $injector.get('$controller')("BodyPointDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'stretchCmsApp:bodyPointUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
