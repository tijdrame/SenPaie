import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DetailPret } from './detail-pret.model';
import { DetailPretService } from './detail-pret.service';

@Component({
    selector: 'jhi-detail-pret-detail',
    templateUrl: './detail-pret-detail.component.html'
})
export class DetailPretDetailComponent implements OnInit, OnDestroy {

    detailPret: DetailPret;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private detailPretService: DetailPretService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDetailPrets();
    }

    load(id) {
        this.detailPretService.find(id)
            .subscribe((detailPretResponse: HttpResponse<DetailPret>) => {
                this.detailPret = detailPretResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDetailPrets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'detailPretListModification',
            (response) => this.load(this.detailPret.id)
        );
    }
}
