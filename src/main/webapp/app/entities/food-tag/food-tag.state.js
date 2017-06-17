(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('food-tag', {
            parent: 'entity',
            url: '/food-tag',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.foodTag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/food-tag/food-tags.html',
                    controller: 'FoodTagController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('foodTag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('food-tag-detail', {
            parent: 'food-tag',
            url: '/food-tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.foodTag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/food-tag/food-tag-detail.html',
                    controller: 'FoodTagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('foodTag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FoodTag', function($stateParams, FoodTag) {
                    return FoodTag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'food-tag',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('food-tag-detail.edit', {
            parent: 'food-tag-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food-tag/food-tag-dialog.html',
                    controller: 'FoodTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FoodTag', function(FoodTag) {
                            return FoodTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('food-tag.new', {
            parent: 'food-tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food-tag/food-tag-dialog.html',
                    controller: 'FoodTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('food-tag', null, { reload: 'food-tag' });
                }, function() {
                    $state.go('food-tag');
                });
            }]
        })
        .state('food-tag.edit', {
            parent: 'food-tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food-tag/food-tag-dialog.html',
                    controller: 'FoodTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FoodTag', function(FoodTag) {
                            return FoodTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('food-tag', null, { reload: 'food-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('food-tag.delete', {
            parent: 'food-tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/food-tag/food-tag-delete-dialog.html',
                    controller: 'FoodTagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FoodTag', function(FoodTag) {
                            return FoodTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('food-tag', null, { reload: 'food-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
