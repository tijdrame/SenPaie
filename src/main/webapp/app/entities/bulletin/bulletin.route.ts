import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BulletinComponent } from './bulletin.component';
import { BulletinDetailComponent } from './bulletin-detail.component';
import { BulletinPopupComponent } from './bulletin-dialog.component';
import { BulletinDeletePopupComponent } from './bulletin-delete-dialog.component';

@Injectable()
export class BulletinResolvePagingParams implements Resolve<any> {

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

export const bulletinRoute: Routes = [
    {
        path: 'bulletin',
        component: BulletinComponent,
        resolve: {
            'pagingParams': BulletinResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bulletin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bulletin/:id',
        component: BulletinDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bulletin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bulletinPopupRoute: Routes = [
    {
        path: 'bulletin-new',
        component: BulletinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bulletin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bulletin/:id/edit',
        component: BulletinPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bulletin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bulletin/:id/delete',
        component: BulletinDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bulletin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
