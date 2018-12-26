import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AvantageCollabComponent } from './avantage-collab.component';
import { AvantageCollabDetailComponent } from './avantage-collab-detail.component';
import { AvantageCollabPopupComponent } from './avantage-collab-dialog.component';
import { AvantageCollabDeletePopupComponent } from './avantage-collab-delete-dialog.component';

@Injectable()
export class AvantageCollabResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const avantageCollabRoute: Routes = [
    {
        path: 'avantage-collab',
        component: AvantageCollabComponent,
        resolve: {
            'pagingParams': AvantageCollabResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantageCollab.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'avantage-collab/:id',
        component: AvantageCollabDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantageCollab.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const avantageCollabPopupRoute: Routes = [
    {
        path: 'avantage-collab-new',
        component: AvantageCollabPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantageCollab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'avantage-collab/:id/edit',
        component: AvantageCollabPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantageCollab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'avantage-collab/:id/delete',
        component: AvantageCollabDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.avantageCollab.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
