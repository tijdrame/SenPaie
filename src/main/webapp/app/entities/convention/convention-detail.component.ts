import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Convention } from './convention.model';
import { ConventionService } from './convention.service';

@Component({
    selector: 'jhi-convention-detail',
    templateUrl: './convention-detail.component.html'
})
export class ConventionDetailComponent implements OnInit, OnDestroy {

    convention: Convention;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private conventionService: ConventionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInConventions();
    }

    load(id) {
        this.conventionService.find(id)
            .subscribe((conventionResponse: HttpResponse<Convention>) => {
                this.convention = conventionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInConventions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'conventionListModification',
            (response) => this.load(this.convention.id)
        );
    }
}
