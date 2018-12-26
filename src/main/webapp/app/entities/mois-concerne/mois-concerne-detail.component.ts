import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MoisConcerne } from './mois-concerne.model';
import { MoisConcerneService } from './mois-concerne.service';

@Component({
    selector: 'jhi-mois-concerne-detail',
    templateUrl: './mois-concerne-detail.component.html'
})
export class MoisConcerneDetailComponent implements OnInit, OnDestroy {

    moisConcerne: MoisConcerne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private moisConcerneService: MoisConcerneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMoisConcernes();
    }

    load(id) {
        this.moisConcerneService.find(id)
            .subscribe((moisConcerneResponse: HttpResponse<MoisConcerne>) => {
                this.moisConcerne = moisConcerneResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMoisConcernes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'moisConcerneListModification',
            (response) => this.load(this.moisConcerne.id)
        );
    }
}
