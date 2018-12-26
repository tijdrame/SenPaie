import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Recap } from './recap.model';
import { RecapService } from './recap.service';

@Component({
    selector: 'jhi-recap-detail',
    templateUrl: './recap-detail.component.html'
})
export class RecapDetailComponent implements OnInit, OnDestroy {

    recap: Recap;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private recapService: RecapService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRecaps();
    }

    load(id) {
        this.recapService.find(id)
            .subscribe((recapResponse: HttpResponse<Recap>) => {
                this.recap = recapResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRecaps() {
        this.eventSubscriber = this.eventManager.subscribe(
            'recapListModification',
            (response) => this.load(this.recap.id)
        );
    }
}
