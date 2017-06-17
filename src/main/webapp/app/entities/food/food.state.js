(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('food', {
            parent: 'entity',
            url: '/food',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.food.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/food/foods.html',
                    controller: 'FoodController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('food');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('food-detail', {
            parent: 'food',
            url: '/food/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.food.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/food/food-detail.html',
                    controller: 'FoodDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('food');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Food', function($stateParams, Food) {
                    return Food.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'food',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('food-detail.edit', {
            parent: 'food-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food/food-dialog.html',
                    controller: 'FoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Food', function(Food) {
                            return Food.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('food.new', {
            parent: 'food',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food/food-dialog.html',
                    controller: 'FoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                image: null,
                                description: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('food', null, { reload: 'food' });
                }, function() {
                    $state.go('food');
                });
            }]
        })
        .state('food.edit', {
            parent: 'food',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food/food-dialog.html',
                    controller: 'FoodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Food', function(Food) {
                            return Food.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('food', null, { reload: 'food' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('food.delete', {
            parent: 'food',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food/food-delete-dialog.html',
                    controller: 'FoodDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Food', function(Food) {
                            return Food.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('food', null, { reload: 'food' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
