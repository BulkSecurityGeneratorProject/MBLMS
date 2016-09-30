(function() {
    'use strict';
    angular
        .module('mblmsApp')
        .factory('UserProperties', UserProperties);

    UserProperties.$inject = ['$resource'];

    function UserProperties ($resource) {
        var resourceUrl =  'api/user-properties/:id';

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
