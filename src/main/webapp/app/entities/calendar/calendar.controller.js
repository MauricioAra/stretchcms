(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('CalendarController', CalendarController);

    CalendarController.$inject = ['Calendar'];

    function CalendarController(Calendar) {

        var vm = this;

        vm.calendars = [];

        loadAll();

        function loadAll() {
            Calendar.query(function(result) {
                vm.calendars = result;
                vm.searchQuery = null;
            });
        }
    }
})();
