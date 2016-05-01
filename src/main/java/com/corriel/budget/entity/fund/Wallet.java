package com.corriel.budget.entity.fund;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("wallet")
public class Wallet extends Fund {
}
