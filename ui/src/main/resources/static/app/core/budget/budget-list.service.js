angular.
module('core.budget').
factory('BudgetList', ['$resource',
    function($resource) {
        return $resource('api/budget/list', {}, {
            query: {
                method: 'GET',
                isArray: true
            }
        });
    }
]);