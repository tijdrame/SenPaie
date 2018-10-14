import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DemandeConge } from './demande-conge.model';
import { DemandeCongeService } from './demande-conge.service';
import {TypeAbsence, TypeAbsenceService} from "../type-absence";

@Component({
    selector: 'jhi-demande-conge-detail',
    templateUrl: './demande-conge-detail.component.html'
})
export class DemandeCongeDetailComponent implements OnInit, OnDestroy {

    demandeConge: DemandeConge;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private demandeCongeService: DemandeCongeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDemandeConges();
    }

    load(id) {
        this.demandeCongeService.find(id)
            .subscribe((demandeCongeResponse: HttpResponse<DemandeConge>) => {
                this.demandeConge = demandeCongeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDemandeConges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'demandeCongeListModification',
            (response) => this.load(this.demandeConge.id)
        );
    }
}
