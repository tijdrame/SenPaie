import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Remboursement } from './remboursement.model';
import { RemboursementService } from './remboursement.service';

@Component({
    selector: 'jhi-remboursement-detail',
    templateUrl: './remboursement-detail.component.html'
})
export class RemboursementDetailComponent implements OnInit, OnDestroy {

    remboursement: Remboursement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private remboursementService: RemboursementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRemboursements();
    }

    load(id) {
        this.remboursementService.find(id)
            .subscribe((remboursementResponse: HttpResponse<Remboursement>) => {
                this.remboursement = remboursementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRemboursements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'remboursementListModification',
            (response) => this.load(this.remboursement.id)
        );
    }
}
