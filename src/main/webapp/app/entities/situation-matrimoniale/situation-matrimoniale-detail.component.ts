import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SituationMatrimoniale } from './situation-matrimoniale.model';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';

@Component({
    selector: 'jhi-situation-matrimoniale-detail',
    templateUrl: './situation-matrimoniale-detail.component.html'
})
export class SituationMatrimonialeDetailComponent implements OnInit, OnDestroy {

    situationMatrimoniale: SituationMatrimoniale;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private situationMatrimonialeService: SituationMatrimonialeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSituationMatrimoniales();
    }

    load(id) {
        this.situationMatrimonialeService.find(id)
            .subscribe((situationMatrimonialeResponse: HttpResponse<SituationMatrimoniale>) => {
                this.situationMatrimoniale = situationMatrimonialeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSituationMatrimoniales() {
        this.eventSubscriber = this.eventManager.subscribe(
            'situationMatrimonialeListModification',
            (response) => this.load(this.situationMatrimoniale.id)
        );
    }
}
