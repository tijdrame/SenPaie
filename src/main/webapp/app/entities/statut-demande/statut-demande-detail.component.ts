import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { StatutDemande } from './statut-demande.model';
import { StatutDemandeService } from './statut-demande.service';

@Component({
    selector: 'jhi-statut-demande-detail',
    templateUrl: './statut-demande-detail.component.html'
})
export class StatutDemandeDetailComponent implements OnInit, OnDestroy {

    statutDemande: StatutDemande;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private statutDemandeService: StatutDemandeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStatutDemandes();
    }

    load(id) {
        this.statutDemandeService.find(id)
            .subscribe((statutDemandeResponse: HttpResponse<StatutDemande>) => {
                this.statutDemande = statutDemandeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStatutDemandes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'statutDemandeListModification',
            (response) => this.load(this.statutDemande.id)
        );
    }
}
