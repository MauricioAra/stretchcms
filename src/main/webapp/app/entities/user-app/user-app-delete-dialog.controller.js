(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('UserAppDeleteController',UserAppDeleteController);

    UserAppDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserApp'];

    function UserAppDeleteController($uibModalInstance, entity, UserApp) {
        var vm = this;

        vm.userApp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserApp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
