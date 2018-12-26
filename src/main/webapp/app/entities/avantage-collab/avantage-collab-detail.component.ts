import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AvantageCollab } from './avantage-collab.model';
import { AvantageCollabService } from './avantage-collab.service';

@Component({
    selector: 'jhi-avantage-collab-detail',
    templateUrl: './avantage-collab-detail.component.html'
})
export class AvantageCollabDetailComponent implements OnInit, OnDestroy {

    avantageCollab: AvantageCollab;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private avantageCollabService: AvantageCollabService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAvantageCollabs();
    }

    load(id) {
        this.avantageCollabService.find(id)
            .subscribe((avantageCollabResponse: HttpResponse<AvantageCollab>) => {
                this.avantageCollab = avantageCollabResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAvantageCollabs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'avantageCollabListModification',
            (response) => this.load(this.avantageCollab.id)
        );
    }
}
