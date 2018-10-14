import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeContrat } from './type-contrat.model';
import { TypeContratService } from './type-contrat.service';

@Component({
    selector: 'jhi-type-contrat-detail',
    templateUrl: './type-contrat-detail.component.html'
})
export class TypeContratDetailComponent implements OnInit, OnDestroy {

    typeContrat: TypeContrat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeContratService: TypeContratService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeContrats();
    }

    load(id) {
        this.typeContratService.find(id)
            .subscribe((typeContratResponse: HttpResponse<TypeContrat>) => {
                this.typeContrat = typeContratResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeContrats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeContratListModification',
            (response) => this.load(this.typeContrat.id)
        );
    }
}
