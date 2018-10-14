import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Statut } from './statut.model';
import { StatutService } from './statut.service';

@Component({
    selector: 'jhi-statut-detail',
    templateUrl: './statut-detail.component.html'
})
export class StatutDetailComponent implements OnInit, OnDestroy {

    statut: Statut;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private statutService: StatutService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStatuts();
    }

    load(id) {
        this.statutService.find(id)
            .subscribe((statutResponse: HttpResponse<Statut>) => {
                this.statut = statutResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStatuts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'statutListModification',
            (response) => this.load(this.statut.id)
        );
    }
}
