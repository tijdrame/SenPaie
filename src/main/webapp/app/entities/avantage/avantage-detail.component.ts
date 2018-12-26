import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Avantage } from './avantage.model';
import { AvantageService } from './avantage.service';

@Component({
    selector: 'jhi-avantage-detail',
    templateUrl: './avantage-detail.component.html'
})
export class AvantageDetailComponent implements OnInit, OnDestroy {

    avantage: Avantage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private avantageService: AvantageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAvantages();
    }

    load(id) {
        this.avantageService.find(id)
            .subscribe((avantageResponse: HttpResponse<Avantage>) => {
                this.avantage = avantageResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAvantages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'avantageListModification',
            (response) => this.load(this.avantage.id)
        );
    }
}
