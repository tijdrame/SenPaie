import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PrimeCollab } from './prime-collab.model';
import { PrimeCollabService } from './prime-collab.service';

@Component({
    selector: 'jhi-prime-collab-detail',
    templateUrl: './prime-collab-detail.component.html'
})
export class PrimeCollabDetailComponent implements OnInit, OnDestroy {

    primeCollab: PrimeCollab;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private primeCollabService: PrimeCollabService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrimeCollabs();
    }

    load(id) {
        this.primeCollabService.find(id)
            .subscribe((primeCollabResponse: HttpResponse<PrimeCollab>) => {
                this.primeCollab = primeCollabResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrimeCollabs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'primeCollabListModification',
            (response) => this.load(this.primeCollab.id)
        );
    }
}
