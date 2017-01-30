angular.
module('budget').
component('budgetList', {
    templateUrl: 'app/budget/budget-list.template.html',
    controller: ['BudgetList',
        function BudgetListController(BudgetList) {
            this.budgets = BudgetList.query();
        }
    ]
});
