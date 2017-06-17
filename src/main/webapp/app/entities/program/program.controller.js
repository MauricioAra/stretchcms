(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramController', ProgramController);

    ProgramController.$inject = ['Program'];

    function ProgramController(Program) {

        var vm = this;

        vm.programs = [];

        loadAll();

        function loadAll() {
            Program.query(function(result) {
                vm.programs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
