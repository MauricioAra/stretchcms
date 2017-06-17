(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserVitalityDeleteController',UserVitalityDeleteController);

    UserVitalityDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserVitality'];

    function UserVitalityDeleteController($uibModalInstance, entity, UserVitality) {
        var vm = this;

        vm.userVitality = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserVitality.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
