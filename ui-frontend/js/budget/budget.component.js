angular.
    module('budget').
    component('budget', {
        template: require('./budget.template.html'),
        controller: ['$rootScope', '$scope', '$location', '$routeParams', 'Budget',
            function BudgetController($rootScope, $scope, $location, $routeParams, Budget) {
                if (!$rootScope.authenticated) {
                    $location.path("/login");
                }

                var self = this;
                var initDetails = function() {
                    self.details = Budget.get({budgetId: $routeParams.id});
                };

                initDetails();

                $scope.$on('transactionInsert', function() {
                    initDetails();
                });
            }
        ]
});