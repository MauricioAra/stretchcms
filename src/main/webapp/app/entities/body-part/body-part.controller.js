(function() {
    'use strict';

    angular
        .module('stretchCmsApp')
        .controller('BodyPartController', BodyPartController);

    BodyPartController.$inject = ['BodyPart'];

    function BodyPartController(BodyPart) {

        var vm = this;

        vm.bodyParts = [];

        loadAll();

        function loadAll() {
            BodyPart.query(function(result) {
                vm.bodyParts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
