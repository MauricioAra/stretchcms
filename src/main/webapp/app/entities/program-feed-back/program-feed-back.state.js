(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('program-feed-back', {
            parent: 'entity',
            url: '/program-feed-back',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.programFeedBack.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/program-feed-back/program-feed-backs.html',
                    controller: 'ProgramFeedBackController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('programFeedBack');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('program-feed-back-detail', {
            parent: 'program-feed-back',
            url: '/program-feed-back/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'stretchCmsApp.programFeedBack.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/program-feed-back/program-feed-back-detail.html',
                    controller: 'ProgramFeedBackDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('programFeedBack');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProgramFeedBack', function($stateParams, ProgramFeedBack) {
                    return ProgramFeedBack.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'program-feed-back',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('program-feed-back-detail.edit', {
            parent: 'program-feed-back-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/program-feed-back/program-feed-back-dialog.html',
                    controller: 'ProgramFeedBackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProgramFeedBack', function(ProgramFeedBack) {
                            return ProgramFeedBack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('program-feed-back.new', {
            parent: 'program-feed-back',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/program-feed-back/program-feed-back-dialog.html',
                    controller: 'ProgramFeedBackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                isUseful: null,
                                isHelpPain: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('program-feed-back', null, { reload: 'program-feed-back' });
                }, function() {
                    $state.go('program-feed-back');
                });
            }]
        })
        .state('program-feed-back.edit', {
            parent: 'program-feed-back',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/program-feed-back/program-feed-back-dialog.html',
                    controller: 'ProgramFeedBackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProgramFeedBack', function(ProgramFeedBack) {
                            return ProgramFeedBack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('program-feed-back', null, { reload: 'program-feed-back' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('program-feed-back.delete', {
            parent: 'program-feed-back',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/program-feed-back/program-feed-back-delete-dialog.html',
                    controller: 'ProgramFeedBackDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProgramFeedBack', function(ProgramFeedBack) {
                            return ProgramFeedBack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('program-feed-back', null, { reload: 'program-feed-back' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
