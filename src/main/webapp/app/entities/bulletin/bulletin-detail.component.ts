import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinService } from './bulletin.service';

@Component({
    selector: 'jhi-bulletin-detail',
    templateUrl: './bulletin-detail.component.html'
})
export class BulletinDetailComponent implements OnInit, OnDestroy {

    bulletin: Bulletin;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bulletinService: BulletinService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBulletins();
    }

    load(id) {
        this.bulletinService.find(id)
            .subscribe((bulletinResponse: HttpResponse<Bulletin>) => {
                this.bulletin = bulletinResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBulletins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bulletinListModification',
            (response) => this.load(this.bulletin.id)
        );
    }
}
