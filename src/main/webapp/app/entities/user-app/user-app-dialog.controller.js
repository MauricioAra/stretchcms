(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserAppDialogController', UserAppDialogController);

    UserAppDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserApp', 'User', 'UserHealth', 'Program', 'UserVitality', 'Exercise'];

    function UserAppDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserApp, User, UserHealth, Program, UserVitality, Exercise) {
        var vm = this;

        vm.userApp = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.userhealths = UserHealth.query({filter: 'userapp(name)-is-null'});
        $q.all([vm.userApp.$promise, vm.userhealths.$promise]).then(function() {
            if (!vm.userApp.userHealthId) {
                return $q.reject();
            }
            return UserHealth.get({id : vm.userApp.userHealthId}).$promise;
        }).then(function(userHealth) {
            vm.userhealths.push(userHealth);
        });
        vm.programs = Program.query();
        vm.uservitalities = UserVitality.query();
        vm.exercises = Exercise.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userApp.id !== null) {
                UserApp.update(vm.userApp, onSaveSuccess, onSaveError);
            } else {
                UserApp.save(vm.userApp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('stretchCmsApp:userAppUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
