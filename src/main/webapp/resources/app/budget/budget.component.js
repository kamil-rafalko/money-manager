angular.
    module('budget').
    component('budget', {
        templateUrl: 'app/budget/budget.template.html',
        controller: ['$rootScope', '$location', '$routeParams', 'Budget',
            function BudgetController($rootScope, $location, $routeParams, Budget) {
                if (!$rootScope.authenticated) {
                    $location.path("/login");
                }
                this.details = Budget.get({budgetId: $routeParams.id});
            }
        ]
});