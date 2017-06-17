(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-vitality', {
            parent: 'entity',
            url: '/user-vitality',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.userVitality.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-vitality/user-vitalities.html',
                    controller: 'UserVitalityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userVitality');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-vitality-detail', {
            parent: 'user-vitality',
            url: '/user-vitality/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.userVitality.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-vitality/user-vitality-detail.html',
                    controller: 'UserVitalityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userVitality');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserVitality', function($stateParams, UserVitality) {
                    return UserVitality.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-vitality',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-vitality-detail.edit', {
            parent: 'user-vitality-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-vitality/user-vitality-dialog.html',
                    controller: 'UserVitalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserVitality', function(UserVitality) {
                            return UserVitality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-vitality.new', {
            parent: 'user-vitality',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-vitality/user-vitality-dialog.html',
                    controller: 'UserVitalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comment: null,
                                range: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-vitality', null, { reload: 'user-vitality' });
                }, function() {
                    $state.go('user-vitality');
                });
            }]
        })
        .state('user-vitality.edit', {
            parent: 'user-vitality',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-vitality/user-vitality-dialog.html',
                    controller: 'UserVitalityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserVitality', function(UserVitality) {
                            return UserVitality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-vitality', null, { reload: 'user-vitality' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-vitality.delete', {
            parent: 'user-vitality',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-vitality/user-vitality-delete-dialog.html',
                    controller: 'UserVitalityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserVitality', function(UserVitality) {
                            return UserVitality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-vitality', null, { reload: 'user-vitality' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
