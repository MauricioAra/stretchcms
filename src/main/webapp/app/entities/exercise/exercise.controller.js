(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('ExerciseController', ExerciseController);

    ExerciseController.$inject = ['Exercise'];

    function ExerciseController(Exercise) {

        var vm = this;

        vm.exercises = [];

        loadAll();

        function loadAll() {
            Exercise.query(function(result) {
                vm.exercises = result;
                vm.searchQuery = null;
            });
        }
    }
})();
