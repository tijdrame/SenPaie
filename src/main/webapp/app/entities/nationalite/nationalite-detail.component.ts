import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Nationalite } from './nationalite.model';
import { NationaliteService } from './nationalite.service';

@Component({
    selector: 'jhi-nationalite-detail',
    templateUrl: './nationalite-detail.component.html'
})
export class NationaliteDetailComponent implements OnInit, OnDestroy {

    nationalite: Nationalite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nationaliteService: NationaliteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNationalites();
    }

    load(id) {
        this.nationaliteService.find(id)
            .subscribe((nationaliteResponse: HttpResponse<Nationalite>) => {
                this.nationalite = nationaliteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNationalites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nationaliteListModification',
            (response) => this.load(this.nationalite.id)
        );
    }
}
