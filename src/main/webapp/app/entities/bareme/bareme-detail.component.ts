import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bareme } from './bareme.model';
import { BaremeService } from './bareme.service';

@Component({
    selector: 'jhi-bareme-detail',
    templateUrl: './bareme-detail.component.html'
})
export class BaremeDetailComponent implements OnInit, OnDestroy {

    bareme: Bareme;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private baremeService: BaremeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBaremes();
    }

    load(id) {
        this.baremeService.find(id)
            .subscribe((baremeResponse: HttpResponse<Bareme>) => {
                this.bareme = baremeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBaremes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'baremeListModification',
            (response) => this.load(this.bareme.id)
        );
    }
}
