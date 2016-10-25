(function() {
    'use strict';
    angular
        .module('mblmsApp')
        .factory('Educator', Educator);

    Educator.$inject = ['$resource'];

    function Educator ($resource) {
        var resourceUrl =  'api/educators/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
