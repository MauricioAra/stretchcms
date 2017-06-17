(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ProgramFeedBackController', ProgramFeedBackController);

    ProgramFeedBackController.$inject = ['ProgramFeedBack'];

    function ProgramFeedBackController(ProgramFeedBack) {

        var vm = this;

        vm.programFeedBacks = [];

        loadAll();

        function loadAll() {
            ProgramFeedBack.query(function(result) {
                vm.programFeedBacks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
