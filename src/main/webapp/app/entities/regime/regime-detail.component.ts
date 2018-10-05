import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Regime } from './regime.model';
import { RegimeService } from './regime.service';

@Component({
    selector: 'jhi-regime-detail',
    templateUrl: './regime-detail.component.html'
})
export class RegimeDetailComponent implements OnInit, OnDestroy {

    regime: Regime;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private regimeService: RegimeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRegimes();
    }

    load(id) {
        this.regimeService.find(id)
            .subscribe((regimeResponse: HttpResponse<Regime>) => {
                this.regime = regimeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRegimes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'regimeListModification',
            (response) => this.load(this.regime.id)
        );
    }
}
