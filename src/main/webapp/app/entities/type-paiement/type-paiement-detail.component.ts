import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypePaiement } from './type-paiement.model';
import { TypePaiementService } from './type-paiement.service';

@Component({
    selector: 'jhi-type-paiement-detail',
    templateUrl: './type-paiement-detail.component.html'
})
export class TypePaiementDetailComponent implements OnInit, OnDestroy {

    typePaiement: TypePaiement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typePaiementService: TypePaiementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypePaiements();
    }

    load(id) {
        this.typePaiementService.find(id)
            .subscribe((typePaiementResponse: HttpResponse<TypePaiement>) => {
                this.typePaiement = typePaiementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypePaiements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typePaiementListModification',
            (response) => this.load(this.typePaiement.id)
        );
    }
}
